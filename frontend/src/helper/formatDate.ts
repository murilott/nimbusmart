export const formatDate = (date: Date | null): string => {
  if (!date) return "";
  
  return date.toLocaleDateString('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: "2-digit",
    minute: "2-digit",
  });
};
